import { Response, Request } from "express";

const postData = async (req: Request, res: Response) => {};

const getDailyData = async (req: Request, res: Response) => {};

const getWeeklyData = async (req: Request, res: Response) => {};

const getMonthlyData = async (req: Request, res: Response) => {};

export default {
    postData: postData,
    getDailyData: getDailyData,
    getWeeklyData: getWeeklyData,
    getMonthlyData: getMonthlyData,
};
