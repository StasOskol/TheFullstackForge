export type SortObject = {
    sorted: boolean,
    empty: boolean,
    unsorted: boolean
}

export type PageableObject<T = Record<string, unknown>> = {
    totalElements: number;
    totalPages: number;
    pageable: PageableObject<T>;
    size: number;
    content: T[];
    number: number;
    sort: SortObject;
    numberOfElements: number;
    first: boolean;
    last: boolean;
    empty: boolean;
}