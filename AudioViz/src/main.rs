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

    println!("Output device: {}", device.name().unwrap());

    let (zender, receiver) = channel();
    static mut sender: Option<std::sync::mpsc::Sender<Vec<f32>>> = None;
    unsafe {
        sender = Some(zender);
    }

    fn data_cb(data: &[f32], _: &cpal::InputCallbackInfo) {
        let d = data.to_owned();
        unsafe {
            println!("Length: {}", d.len());
            sender.as_ref().unwrap().send(d).expect("I dunno what to expect anymore.");
        }
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

    stream.unwrap().play()?;

    loop {
        let data = receiver.recv().unwrap();
        if data.len() == 0 {
            println!("EMPTY");
        }
        println!("{:?}", data);
    }

    return Ok(());
}
