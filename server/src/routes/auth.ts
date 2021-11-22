import { Router } from "express";
import authController from "../controllers/auth-controller";

const authRouter = Router();

authRouter.post("/signup", authController.signUp);

authRouter.post("/login", authController.login);

export { authRouter };
