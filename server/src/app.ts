// src/app.ts
import * as dotenv from "dotenv";
import express, { Response, Request, NextFunction } from "express";
import cors from "cors";

import { sequelize } from "./models";

dotenv.config();
// * APP VARIABLES
const PORT: number = parseInt(process.env.PORT as string, 10) || 5000;
const HOST: string = process.env.HOST || "localhost";
const app: express.Application = express();

// * APP CONFIGURATION: middleware
app.use(cors());
app.use(express.json());
app.use((req: Request, res: Response, next: NextFunction) => {
    console.log(`Request occur! ${req.method}, ${req.url}`);
    next();
});

// TODO ROUTER SETTING

// get
app.get("/", (req: Request, res: Response) => {
    res.send("hello express");
});

// 5000 포트로 서버 실행
app.listen(PORT, HOST, async () => {
    console.log(`server on: listening on ${HOST}:${PORT}`);
    // sequelize-db connection test
    await sequelize
        .authenticate()
        .then(async () => {
            console.log("seq connection success");
        })
        .catch((e) => {
            console.log("seq ERROR: ", e);
        });
});
