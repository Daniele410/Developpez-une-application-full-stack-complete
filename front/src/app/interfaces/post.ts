import { Topics } from "./topics.interface";
import { User } from "./user.interface";

export interface Post { 
    id: number,
    topic: Topics,
    author: User,
    title: string,
    content: string,
    topic_id: number,
    createdAt: Date,
    updatedAt: Date,
}
