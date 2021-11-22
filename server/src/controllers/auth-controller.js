"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    Object.defineProperty(o, k2, { enumerable: true, get: function() { return m[k]; } });
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const dotenv = __importStar(require("dotenv"));
const user_model_1 = __importDefault(require("../models/user.model"));
const bcryptjs_1 = __importDefault(require("bcryptjs"));
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const signUp = async (req, res) => {
    // TODO make, save, and send token
    // * Validate user input
    if (!req.body.email || !req.body.password || !req.body.nickname) {
        res.status(400).send({ status: 400, message: "Fail SignUp" });
        return;
    }
    const { email, password, nickname } = req.body;
    // * Validate if email already exists
    let isEmailExist = false;
    await user_model_1.default.findOne({ where: { email: email } })
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
        let encryptedPassword = await bcryptjs_1.default.hash(password, 10);
        const token = jsonwebtoken_1.default.sign({ email }, TOKEN_KEY, {
            expiresIn: "999999h",
        });
        new user_model_1.default({
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
const login = async (req, res) => {
    // * Validate user input
    if (!req.body.email || !req.body.password) {
        res.status(400).send({
            status: 400,
            message: "email and password is both required",
        });
        return;
    }
    const { email, password } = req.body;
    await user_model_1.default.findOne({
        where: {
            email: email,
        },
        attributes: ["id", "password", "android_token"],
    })
        .then(async (data) => {
        // * Validate if email already exists
        if (data) {
            const isPasswordCorrect = await bcryptjs_1.default.compare(password, data.password);
            // * Validate if password is correct
            if (isPasswordCorrect) {
                res.status(200).send({
                    status: 200,
                    message: "Login Success",
                    token: data.android_token,
                });
            }
            else {
                res.status(400).send({
                    status: 400,
                    message: "Wrong password",
                });
            }
        }
        else {
            res.status(400).send({
                status: 400,
                message: "Wrong email",
            });
        }
    }, (e) => {
        throw e;
    })
        .catch((e) => {
        console.log(e);
        res.status(400).send({
            status: 400,
            message: "Server Error",
        });
    });
};
exports.default = {
    signUp: signUp,
    login: login,
};
