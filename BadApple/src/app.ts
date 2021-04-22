const gifFrames = require('gif-frames');
const decode = require('image-decode');
const sound = require('sound-play');

import { Readable } from 'stream';
import { Config } from './config';

type FrameData = {
    getImage(): Readable,
    frameIndex: number,
    frameInfo: any
}

type RGB = {
    r: number,
    g: number,
    b: number
}

type Image = {
    data: RGB[][]
}

export class App {
    private readonly config = Config.get();

    private static started = false;

    private time = Date.now();
    private next = Date.now();

    public async run(): Promise<void> {
        const frames = await this.getAllFrames();
        const FPS = this.config.FPS;
        
        for (const frame of frames) {
            this.time = Date.now();
            const dt = this.time - this.next;
            this.next = this.time;

            if (dt < 1000 / FPS) {
                await App.sleep(1000 / FPS - dt);
            }

            const frameStream: Readable = frame.getImage();
            const buffer = await this.readStream(frameStream);

            let { data } = decode(buffer);

            let imgRow: RGB[] = [];

            const image: Image = { data: [] as RGB[][] };
            
            for (let pos = 4; pos <= data.length; pos += 4) {
                const rgb = {
                    r: data[pos - 4],
                    g: data[pos - 3],
                    b: data[pos - 2]
                } as RGB;

                imgRow.push(rgb);
                
                if (pos % (this.config.WIDTH * 4) === 0) {
                    image.data.push(imgRow);
                    imgRow = [];
                }
            }

            this.draw(image);
        }
    }

    private async getAllFrames(): Promise<FrameData[]> {
        const frameData: FrameData[] = await gifFrames({
            url: this.config.FILE_PATH,
            frames: 'all',
            cumulative: true
        });

        return frameData;
    }

    private readStream(stream: Readable): Promise<Buffer> {
        return new Promise<Buffer>(resolve => {
            const fragments = [] as Buffer[];
            stream.on('data', (data) => fragments.push(data));
            stream.on('end', () => resolve(Buffer.concat(fragments)));
        });
    }

    private draw(image: Image): void {
        console.clear();

        let message: string = "";

        for (let y = 0; y < this.config.HEIGHT; y++) {
            for (let x = 0; x < this.config.WIDTH; x++) {

                const { r, g, b } = image.data[y][x];

                if (r >= 128 && g >= 128 && b >= 128) {
                    message += 'O';
                }
                else {
                    message += ' ';
                }
            }

            message += '\n';
        }

        if (!App.started) {
            sound.play(this.config.AUDIO_PATH);
            App.started = true;
        }

        console.log(message);
    }

    static sleep(ms: number) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
}