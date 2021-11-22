import { Response, Request } from "express";

import { loginService, signUpService } from "../services/auth-services";
import { serviceReturnForm } from "../modules/service-modules";

const signUp = async (req: Request, res: Response) => {
    // * Validate user input
    if (!req.body.email || !req.body.password || !req.body.nickname) {
        res.status(400).send({ status: 400, message: "Fail SignUp" });
        return;
    }
    const { email, password, nickname } = req.body;

    const returnData: serviceReturnForm = await signUpService(
        email,
        password,
        nickname
    );
    if (returnData.status == 200) {
        // when successed
        const { status, message, responseData } = returnData;
        res.status(status).send({
            status,
            message,
            responseData,
        });
    } else {
        // when failed
        const { status, message } = returnData;
        res.status(status).send({
            status,
            message,
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
    const returnData: serviceReturnForm = await loginService(email, password);
    if (returnData.status == 200) {
        // when successed
        const { status, message, responseData } = returnData;
        res.status(status).send({
            status,
            message,
            responseData,
        });
    } else {
        // when failed
        const { status, message } = returnData;
        res.status(status).send({
            status,
            message,
        });
    }
};

export default {
    signUp: signUp,
    login: login,
};
