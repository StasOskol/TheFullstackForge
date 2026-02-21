export type ApiResponse < T > = {
    data: T;
    status: number;
    message?: string;
}

export type Pageable = {
    page: number;
    size: number;
    sort?: string[];
}

export const getDefaultPageable = (): Pageable => {
    return { page: 0, size: 100, sort: ["id"] }
}