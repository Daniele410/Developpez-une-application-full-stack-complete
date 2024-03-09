export interface Comment {
    id: number;
    description: string;
    authorId: number;
    postId: number;
    createdAt: Date;
    updatedAt: Date;
}
