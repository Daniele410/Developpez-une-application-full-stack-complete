import { User } from "./user.interface";

export interface Comment {
    id: number;
    description: string;
    author: User;
    postId: number;
    createdAt: Date;
    updatedAt: Date;
    postTitle: string;
}
