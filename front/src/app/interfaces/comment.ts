import { User } from "./user.interface";

export interface Comment {
    id: number;
    description: string;
    authorId: User;
    postId: number;
    createdAt: Date;
    updatedAt: Date;
}
