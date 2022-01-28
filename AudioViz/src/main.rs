use cpal::traits::{ DeviceTrait, StreamTrait };
use cpal::{ StreamError, InputCallbackInfo };
use std::sync::mpsc::*;

//use portaudio;

mod provider;

fn main() -> Result<(), cpal::PlayStreamError> {
    let provider = provider::Provider::new();

    let host = provider.get_host();
    let device = provider.get_device();
    let sup_config = provider.get_sup_config();
    let config = sup_config.config();

    //let config = cpal::StreamConfig {
    //    channels: 2,
    //    buffer_size: cpal::BufferSize::Default,
    //    sample_rate: cpal::SampleRate(44100)
    //};

    println!("Host: {}", host.id().name());
    println!("Output device: {}", device.name().unwrap());
    println!("Config > Channels: {}", config.channels);
    println!("Config > Buffer Size: {:?}", config.buffer_size);
    println!("Config > Sample Rate: {:?}", config.sample_rate);

    let (zender, receiver) = channel();
    static mut sender: Option<Sender<f32>> = None; // Vector<f32>
    unsafe {
        sender = Some(zender);
    }

    fn data_cb(data: &[f32], _: &InputCallbackInfo) {
        //let d = data.to_owned();

        for &sample in data {
            unsafe {
                sender.as_ref().unwrap().send(sample).ok();
            }
        }

        /*unsafe {
            println!("Length: {}", d.len());
            sender.as_ref().unwrap().send(d).expect("I dunno what to expect anymore.");
        }*/
    }

    fn err_cb(err: StreamError) {
        println!("Error: {}", err);
        return;
    }

    let stream = device.build_input_stream(
        &config,
        data_cb,
        err_cb
    );

    match stream {
        Ok(_) => {
            stream.unwrap().play()?;
            println!("Building stream...");
        }
        Err(_) => {
            println!("Error: Stream unable to start!");
        }
    }

    let data = receiver.try_recv().unwrap();
    println!("{:?}", data);

    return Ok(());
}
