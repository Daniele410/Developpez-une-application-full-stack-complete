import { Topics } from "./topics.interface";
import { User } from "./user.interface";

export interface Post { 
    id: number,
    topic: Topics,
    author: User,
    title: string,
    description: string,
    topics: string,
    createdAt: Date,
    updatedAt: Date,
}
