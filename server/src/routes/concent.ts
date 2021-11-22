import { Router } from "express";
import concentController from "../controllers/concent-controller";

const concentRouter = Router();

concentRouter.post("/data", concentController.postData);

concentRouter.get("/daily_data", concentController.getDailyData);

concentRouter.get("/weekly_data", concentController.getWeeklyData);

concentRouter.get("/monthly_data", concentController.getMonthlyData);

export { concentRouter };
