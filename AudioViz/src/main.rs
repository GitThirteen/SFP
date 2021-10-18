use portaudio;

fn main() {
    let port = portaudio::PortAudio::new().expect("Unable to initialize PortAudio.");

    let out_index = port.default_output_device().expect("Unable to get default output device.");
    let out = port.device_info(out_index).expect("No device could be found.");
    println!("{}", out.name);
}
