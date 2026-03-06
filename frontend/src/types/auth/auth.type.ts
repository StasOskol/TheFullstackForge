export type authLoginDto = {
    login: string,
    password: string
}

export type resAuthLoginDto = {
    token: string,
    type: string,
    userId: number,
    username: string
}