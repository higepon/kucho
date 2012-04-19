#  Copyright 2012 Twitter, Inc.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

require 'rubygems'
require 'yaml'
require 'api/air_remote'

config = YAML.load_file("#{Dir::pwd}/config.yml")
remote = AirRemote.new(config["url"])

delta = ARGV.shift.to_i

puts "ARGV=#{ARGV}"

warmer = delta > 0

ts = remote.fetch_temperatures(ARGV)
if warmer
  warmest = ts.max {|a, b| a[:temperature] <=> b[:temperature] }
  if warmest[:temperature] == 5
    puts "ng too hot" 
    exit
  end
else
  coolest = ts.min {|a, b| a[:temperature] <=> b[:temperature] }
  if coolest[:temperature] == 1
    puts "ng too cool" 
    exit
  end
end

# return status immediately
puts "ok"

# pp ts

delta.abs.times {|n|
  if (warmer)
    coolest = ts.min {|a, b| a[:temperature] <=> b[:temperature] }
    if coolest[:temperature] != 5
      coolest[:temperature] = coolest[:temperature] + 1
      coolest[:dirty] = true
    end
  else 
    warmest = ts.max {|a, b| a[:temperature] <=> b[:temperature] }
    if warmest[:temperature] != 1
      warmest[:temperature] = warmest[:temperature] - 1
      warmest[:dirty] = true
    end
  end
}

# pp ts

ts.each {|t|
  if t[:dirty]
    STDERR.puts "sleeping"
    sleep 3
    STDERR.puts "set #{t[:dev_id]} #{t[:temperature]}"
    begin
      remote.set_temperature!([t[:dev_id]], t[:temperature])
    rescue => exc
      STDERR.puts "#{exc.to_s}"
    end
  end
}
