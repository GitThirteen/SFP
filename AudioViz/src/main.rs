use cpal::traits::{ DeviceTrait as dt, HostTrait as ht };

mod provider;

fn main() -> Result<(), ()> {
    let provider = provider::Provider::new();

    let host = provider.get_host();
    let device = provider.get_device();
    let sup_config = provider.get_sup_config();

    println!("{}", device.name().unwrap());

    // TODO
    
    return Ok(());
}
