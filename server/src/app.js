"use strict";
// src/app.ts
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var express_1 = __importDefault(require("express"));
var app = (0, express_1.default)();
// get
app.get("/", function (req, res) {
    res.send("hello express");
});
// 5000 포트로 서버 실행
app.listen(5000, function () {
    console.log("실행중");
});
