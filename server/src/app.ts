// src/app.ts

import express from "express";
const app: express.Application = express();

// get
app.get("/", (req: express.Request, res: express.Response) => {
    res.send("hello express");
});

// 5000 포트로 서버 실행
app.listen(5000, () => {
    console.log("server on: listening to port 5000...");
});
