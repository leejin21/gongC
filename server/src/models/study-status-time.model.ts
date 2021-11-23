import {
    Table,
    Column,
    Model,
    AllowNull,
    Unique,
    DataType,
} from "sequelize-typescript";

@Table
export default class StudyStatusTime extends Model {
    // id는 자동으로 auto_increment, primarykey 설정된 채로 추가됨.

    @AllowNull(false)
    @Column(DataType.STRING)
    public userid!: string;

    @AllowNull(false)
    @Column(DataType.STRING)
    public status!: string;
}
