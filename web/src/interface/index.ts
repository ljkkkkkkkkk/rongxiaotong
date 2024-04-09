export interface Good {
    orderId: number,
    title: string,
    price: number,
    content: string,
    orderStatus: number,
    type: string,
    picture: string,
    ownName: string,
    cooperationName: string,
    createTime: string,
    updateTime: string,
    address:string 
}

export interface Knowledge{
    knowledgeId: number;
    title: string;
    content:string;
    picPath:string;
    ownName:string;
    createTime:string;
    updateTime:String;
}
export interface Discuss{
    discussId: number;
    knowledgeId: number;
    ownName:string;
    content:string;
    createTime:String;
}

export interface Result<T = any> {
    code: number;
    message: string;
    data: T;
}

export interface Expert {
    userName: string;
    realName: string;
    phone: string;
    profession: string;
    position: string;
    belong: string;
}

export interface Cart {
    shoppingId: number,
    orderId: number,
    title: string,
    content:string, 
    price: string,
    picture: string,
    ownName: string,
    count: number
}

export interface PageInfo<T> {
    total: number,
    list: T[]
}

export interface Question {
    id: number,
    expertName: string,
    questioner: string,
    phone: string,
    plantName:string, 
    title: string,
    question: string,
    answer: string,
    status: number
}

export interface LoginInfo {
    username: string;
    password: string;
}
export interface Test {
    name: string;
    age: number;
}

export interface Address {
    id?: number,
    ownName: string,
    consignee: string,
    phone: string,
    addressDetail: string,
    isDefault: boolean
}

export interface PasswordInfo {
    oldPassword: string, 
    newPassword: string
}

export interface RegisterInfo {
    userName: string, 
    password: string, 
    nickName: string, 
    avatar:string
}

