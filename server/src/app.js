"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    Object.defineProperty(o, k2, { enumerable: true, get: function() { return m[k]; } });
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// src/app.ts
const dotenv = __importStar(require("dotenv"));
const express_1 = __importDefault(require("express"));
const cors_1 = __importDefault(require("cors"));
const models_1 = require("./models");
const user_model_1 = __importDefault(require("./models/user.model"));
const routes_1 = __importDefault(require("./routes"));
dotenv.config();
// * APP VARIABLES
const PORT = parseInt(process.env.PORT, 10) || 5000;
const HOST = process.env.HOST || "localhost";
const app = (0, express_1.default)();
// * APP CONFIGURATION: middleware
app.use((0, cors_1.default)());
app.use(express_1.default.json());
app.use((req, res, next) => {
    console.log(`Request occur! ${req.method}, ${req.url}`);
    next();
});
// * ROUTER SETTING
app.use(routes_1.default);
// get
app.get("/", (req, res) => {
    res.send("hello express");
});
app.get("/test", (req, res) => {
    // email password nickname rasp_token android_token
    const user = new user_model_1.default({
        email: "test@gmail.com",
        password: "1111",
        nickname: "안녕",
    });
    user.save();
    res.status(200).send({ done: true });
    // res.status(400).send({ done: false });
});
// 5000 포트로 서버 실행
app.listen(PORT, HOST, async () => {
    console.log(`server on: listening on ${HOST}:${PORT}`);
    // sequelize-db connection test
    await models_1.sequelize
        .sync({})
        .then(async () => {
        console.log("seq connection success");
    })
        .catch((e) => {
        console.log("seq ERROR: ", e);
    });
});
