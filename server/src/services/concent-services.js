"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.postDataService = void 0;
const study_status_time_model_1 = __importDefault(require("../models/study-status-time.model"));
const postDataService = async (user, status) => {
    const returnForm = {
        status: 500,
        message: "server error",
        responseData: {},
    };
    // * Create Data
    await new study_status_time_model_1.default({
        userid: user.id,
        status: status,
    })
        .save()
        .then((data) => {
        returnForm.status = 200;
        returnForm.message = "Post Data Success";
    })
        .catch((e) => {
        console.log(e);
        returnForm.status = 500;
        returnForm.message = "Server Error";
    });
    return returnForm;
};
exports.postDataService = postDataService;
