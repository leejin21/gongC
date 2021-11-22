import { Response, Request } from "express";
import { emit } from "process";
import User from "../models/user.model";

const signUp = async (req: Request, res: Response) => {
    // TODO make, save, and send token
    console.log("auth/signup");
    console.log(req.body);
    new User({
        email: req.body.email || "",
        password: req.body.password || "",
        nickname: req.body.nickname || "",
    })
        .save()
        .then(() => {
            res.status(200).send({ status: 200, message: "회원가입 성공" });
        })
        .catch((e) => {
            console.log(e);
            res.status(400).send({ status: 400, message: "회원가입 실패" });
        });
};

const login = async (req: Request, res: Response) => {
    console.log(req.body, req.params);
    // console.log(req);
    await User.findOne({
        where: {
            email: req.body.email || "",
            password: req.body.password || "",
        },
        attributes: ["id"],
    })
        .then(
            (data) => {
                if (data) {
                    console.log("login success");
                    res.status(200).send({
                        status: 200,
                        message: "로그인 성공",
                        data: data.id,
                    });
                } else {
                    console.log("login fail: no email in db");
                    res.status(400).send({
                        status: 400,
                        message: "로그인 실패",
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
                message: "로그인 실패",
            });
        });
};

export default {
    signUp: signUp,
    login: login,
};
