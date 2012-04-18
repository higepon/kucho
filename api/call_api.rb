require 'rubygems'
require 'yaml'
require 'api/air_remote'

def name2dev_id(name)
  return "tcbox#{name[0..2]}0#{name[3..5]}I0#{name[7..8]}"
end

config = YAML.load_file("#{Dir::pwd}/config.yml")
dev_ids = ARGV.map {|arg| name2dev_id(arg) }

remote = AirRemote.new(config["url"])
puts remote.cooler!(dev_ids) ? "ok" : "ng"
