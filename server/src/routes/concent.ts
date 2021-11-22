import { Router } from "express";
import concentController from "../controllers/concent-controller";

const router = Router();

router.post("data", concentController.postData);

router.get("daily_data", concentController.getDailyData);

router.get("weekly_data", concentController.getWeeklyData);

router.get("monthly_data", concentController.getMonthlyData);

export default router;
