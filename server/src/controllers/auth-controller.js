"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const auth_services_1 = require("../services/auth-services");
const signUp = async (req, res) => {
    // * Validate user input
    if (!req.body.email || !req.body.password || !req.body.nickname) {
        res.status(400).send({ status: 400, message: "Fail SignUp" });
        return;
    }
    const { email, password, nickname } = req.body;
    const returnData = await (0, auth_services_1.signUpService)(email, password, nickname);
    if (returnData.status == 200) {
        // when successed
        const { status, message, responseData } = returnData;
        res.status(status).send({
            status,
            message,
            responseData,
        });
    }
    else {
        // when failed
        const { status, message } = returnData;
        res.status(status).send({
            status,
            message,
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
    const returnData = await (0, auth_services_1.loginService)(email, password);
    if (returnData.status == 200) {
        // when successed
        const { status, message, responseData } = returnData;
        res.status(status).send({
            status,
            message,
            responseData,
        });
    }
    else {
        // when failed
        const { status, message } = returnData;
        res.status(status).send({
            status,
            message,
        });
    }
};
exports.default = {
    signUp: signUp,
    login: login,
};
