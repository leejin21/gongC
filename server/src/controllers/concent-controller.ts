import { Response, Request } from "express";
import { serviceReturnForm } from "../modules/service-modules";
import { postDataService } from "../services/concent-services";
import User from "../models/user.model";
// TODO API DOCS에 미들웨어 반영하기

// * CONTROLLER PART
const postData = async (req: Request, res: Response) => {
    // * Validate user input
    if (!req.body.status) {
        res.status(400).send({
            status: 400,
            message: "data field status, time missing",
        });
    }

    const { status } = req.body;
    const user = req.user as User;

    const returnData: serviceReturnForm = await postDataService(user, status);

    res.status(returnData.status).send({
        status: returnData.status,
        message: returnData.message,
    });
};

const getDailyData = async (req: Request, res: Response) => {};

const getWeeklyData = async (req: Request, res: Response) => {};

const getMonthlyData = async (req: Request, res: Response) => {};

export default {
    postData: postData,
    getDailyData: getDailyData,
    getWeeklyData: getWeeklyData,
    getMonthlyData: getMonthlyData,
};
