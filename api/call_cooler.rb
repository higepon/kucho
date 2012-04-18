require 'rubygems'
require 'yaml'
require 'api/air_remote'

config = YAML.load_file("#{Dir::pwd}/config.yml")
remote = AirRemote.new(config["url"])
puts remote.cooler!(ARGV) ? "ok" : "ng"
