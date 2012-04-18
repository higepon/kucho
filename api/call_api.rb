require 'rubygems'
require 'yaml'
require 'api/air_remote'

config = YAML.load_file("#{Dir::pwd}/config.yml")
remote = AirRemote.new(config["url"])
remote.cooler!(['tcboxVAV017EI001', 'tcboxVAV017EI002'])
