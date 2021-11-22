import { Response, Request } from "express";
import * as dotenv from "dotenv";
import User from "../models/user.model";
import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";

const signUp = async (req: Request, res: Response) => {
    // TODO make, save, and send token
    // * Validate user input
    if (!req.body.email || !req.body.password || !req.body.nickname) {
        res.status(400).send({ status: 400, message: "Fail SignUp" });
        return;
    }
    const { email, password, nickname } = req.body;

    // * Validate if email already exists
    let isEmailExist = false;
    await User.findOne({ where: { email: email } })
        .then((data) => {
            if (data) {
                isEmailExist = true;
                res.status(400).send({
                    status: 400,
                    message: "Email already exist",
                });
            }
        })
        .catch((e) => {
            console.log(e);
            res.status(400).send({ status: 400, message: "Server Error" });
            return;
        });

    // * Create User only when the email does not already exists
    dotenv.config();
    const TOKEN_KEY = process.env.TOKEN_KEY || "";
    if (!isEmailExist) {
        // * Encrypt user password
        let encryptedPassword = await bcrypt.hash(password, 10);
        const token = jwt.sign({ email }, TOKEN_KEY, {
            expiresIn: "999999h",
        });
        new User({
            email: email || "",
            password: encryptedPassword || "",
            nickname: nickname || "",
            android_token: token || "",
        })
            .save()
            .then((data) => {
                res.status(200).send({
                    status: 200,
                    message: "SignUp Success",
                    token: data.android_token,
                });
            })
            .catch((e) => {
                console.log(e);
                res.status(400).send({ status: 400, message: "Server Error" });
            });
    }
};

const login = async (req: Request, res: Response) => {
    // * Validate user input
    if (!req.body.email || !req.body.password) {
        res.status(400).send({
            status: 400,
            message: "email and password is both required",
        });
        return;
    }
    const { email, password } = req.body;

    await User.findOne({
        where: {
            email: email,
        },
        attributes: ["id", "password", "android_token"],
    })
        .then(
            async (data) => {
                // * Validate if email already exists
                if (data) {
                    const isPasswordCorrect = await bcrypt.compare(
                        password,
                        data.password
                    );
                    // * Validate if password is correct
                    if (isPasswordCorrect) {
                        res.status(200).send({
                            status: 200,
                            message: "Login Success",
                            token: data.android_token,
                        });
                    } else {
                        res.status(400).send({
                            status: 400,
                            message: "Wrong password",
                        });
                    }
                } else {
                    res.status(400).send({
                        status: 400,
                        message: "Wrong email",
                    });
                }
            },
            (e) => {
                throw e;
            }
        )
        .catch((e) => {
            console.log(e);
            res.status(400).send({
                status: 400,
                message: "Server Error",
            });
        });
};

export default {
    signUp: signUp,
    login: login,
};
