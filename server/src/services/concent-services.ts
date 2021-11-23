import User from "../models/user.model";

import { serviceReturnForm } from "../modules/service-modules";
import StudyStatusTime from "../models/study-status-time.model";

const postDataService = async (user: User, status: string) => {
    const returnForm: serviceReturnForm = {
        status: 500,
        message: "server error",
        responseData: {},
    };
    // * Create Data
    await new StudyStatusTime({
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

export { postDataService };
