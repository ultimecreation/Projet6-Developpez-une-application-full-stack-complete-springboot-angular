import { LoginRequestInterface } from "./LoginRequestInterface"

export interface RegisterRequestInterface extends LoginRequestInterface {
    username: string | null | undefined
}