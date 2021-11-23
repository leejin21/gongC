"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const concent_services_1 = require("../services/concent-services");
// TODO API DOCS에 미들웨어 반영하기
// * CONTROLLER PART
const postData = async (req, res) => {
    // * Validate user input
    if (!req.body.status) {
        res.status(400).send({
            status: 400,
            message: "data field status, time missing",
        });
    }
    const { status } = req.body;
    const user = req.user;
    const returnData = await (0, concent_services_1.postDataService)(user, status);
    res.status(returnData.status).send({
        status: returnData.status,
        message: returnData.message,
    });
};
const getDailyData = async (req, res) => {
    const user = req.user;
    const returnData = await (0, concent_services_1.getDailyDataService)(user);
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
const getWeeklyData = async (req, res) => { };
const getMonthlyData = async (req, res) => { };
exports.default = {
    postData: postData,
    getDailyData: getDailyData,
    getWeeklyData: getWeeklyData,
    getMonthlyData: getMonthlyData,
};
