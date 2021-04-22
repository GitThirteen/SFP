import * as path from 'path';

export class Config {
    private static config: Config;

    readonly FILE_PATH: string  = 'assets/badapple.gif';
    readonly AUDIO_PATH: string = path.join(process.cwd(), 'assets/badapple.mp3');
    readonly WIDTH: number      = 80;
    readonly HEIGHT: number     = 60;
    readonly FPS: number        = 25;

    private constructor() { };

    public static get(): Config {
        if (!Config.config) {
            Config.config = new Config();
        }

        console.log(this.config.AUDIO_PATH);

        return Config.config;
    }
}