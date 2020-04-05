const Winston = require('winston');
const Influx = require('influx');

class DrinkLog {
  constructor() {
    this.winston_ = Winston.createLogger({
      level: 'info',
      format: Winston.format.combine(
        Winston.format.timestamp(),
        Winston.format.json()
      ),
      defaultMeta: { service: 'user-service' },
      transports: [
        new Winston.transports.File({
          filename: 'error.log',
          level: 'error'
        }),
        new Winston.transports.File({
          filename: 'info.log',
          level: 'info',
          timestamp: true
        }),
      ]
    });
    
    this.influx_ = new Influx.InfluxDB({
      host: 'localhost',
      database: 'drinkalot_db',
      schema: [
        {
          measurement: 'drink_mix',
          fields: {
            mix: Influx.FieldType.FLOAT
          },
          tags: []
        }
      ]
    })

    this.influx_.getDatabaseNames().then(names => {
      if (!names.includes('drinkalot_db')) {
        return this.influx_.createDatabase('drinkalot_db');
      }
    });


    /* Has log been sourced? */
    this.sourced_ = false;
    /* Duration timer. */
    this.source_time_ = 0;
  }
  
  /* For accurate logging Source must be called before Sink... */
  SourceLog(mix) {
    if (this.sourced_) {
      return false;
    }

    this.winston_.info(`Pouring ${mix}%J/${100 - mix}%C`);
    this.WriteInflux(mix); 
    this.source_time_ = new Date().getTime();
    this.sourced_ = true;
    return true;
  }
  
  SinkLog() {
    if (!this.sourced_) {
      return false;
    }
    
    const duration = new Date().getTime() - this.source_time_;
    this.winston_.info(`Stopped pouring, duration: ${duration}`);
    this.WriteInflux(0); 
    this.source_time_ = 0;
    this.sourced_ = false; 
    return true;
  }
  WriteInflux(mix) {
    this.influx_.writePoints([
      {
        measurement: 'drink_mix',
        fields: {
          mix: mix
        },
      }
    ]).catch(err => {
      this.winston.error(`Error saving data to InfluxDB! ${err.stack}`)
    });
  }
}

module.exports = new DrinkLog();
