use cpal::traits::{ DeviceTrait, HostTrait };

pub struct Provider {
    host: cpal::Host,
    device: cpal::Device,
    sup_config: cpal::SupportedStreamConfig
}

impl Provider {
    pub fn new() -> Self {
        let host = cpal::default_host();
        let device = Self::gen_device(&host);
        let sup_config = Self::gen_sup_config(&device);

        return Self {
            host,
            device,
            sup_config
        };
    }

    pub fn get_host(&self) -> &cpal::Host {
        return &self.host;
    }

    pub fn get_device(&self) -> &cpal::Device {
        return &self.device;
    }

    pub fn get_sup_config(&self) -> &cpal::SupportedStreamConfig {
        return &self.sup_config;
    }

    fn gen_device(host: &cpal::Host) -> cpal::Device {
        return host.default_output_device()
        .expect("Error: No available output device.");
    }

    fn gen_sup_config(device: &cpal::Device) -> cpal::SupportedStreamConfig {
        let mut sup_configs = device.supported_output_configs()
            .expect("Error: Something went wrong while querying the configs.");

        return sup_configs.next()
            .expect("Error: No supported config.")
            .with_max_sample_rate();
    }
}