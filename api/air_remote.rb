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
require 'pp'
require 'mechanize'

class AirRemote
  def initialize(url)
    @url = url
    @last_error = ''
  end

  def cooler!(names)
    begin
      ts = fetch_temperatures(names)
      warmest = ts.max {|a, b| a[:temperature] <=> b[:temperature] }
      if (warmest[:temperature] == 1)
        @last_error = "too cool!"
        return false
      end
      return set_temperature!([warmest[:dev_id]], warmest[:temperature] - 1)
    rescue => exc
      @last_error = exc.to_s
      return false
    end
  end

  def warmer!(names)
    begin
      ts = fetch_temperatures(names)
      coolest = ts.min {|a, b| a[:temperature] <=> b[:temperature] }
      if (coolest[:temperature] == 5) 
        @last_error = "too warm!"
        return false
      end
      return set_temperature!([coolest[:dev_id]], coolest[:temperature] + 1)
    rescue => exc
      @last_error = exc.to_s
      return false
    end
  end

  def last_error
    return @last_error
  end

private
  def fetch_temperatures(names)
    dev_ids = names.map {|name| name2dev_id(name) }
    return dev_ids.map{|dev_id| sleep 1; { :dev_id => dev_id, :temperature => temperature(dev_id) }}
  end

  def set_temperature!(dev_ids, value)
    if (value.class != Fixnum ||
        value > 5 ||
        value < 0) 
      raise "temperature should be 1, 2, 3, 4 or 5"
    end

    # Select air conditioners
    form = login.form_with(:name => 'Form1')
    dev_ids.each {|dev_id|
      form.checkbox_with(:name => dev_id).check
    }

    # Submit then the air conditioners are shown
    kiki_button = form.button_with(:name => 'btnEquipment')
    reloaded_page = form.submit(kiki_button)
    confirm_form = reloaded_page.form_with(:name => 'Form1')

    # Select temperature
    temp_button =  confirm_form.button_with(:name => "btnTemperatureSetValueButtons#{value - 1}")
    last_page = confirm_form.submit(temp_button)

    last_form = last_page.form_with(:name => 'Form1')

    # Go!
    Thread.new do
      last_form.submit(last_form.button_with(:name => 'btnAdjust'))
    end
    return true
  end

  def temperature(dev_id)
    # Select air conditioner
    form = login.form_with(:name => 'Form1')
    begin
      form.checkbox_with(:name => dev_id).check
    rescue => exc
      raise "unknown air conditioner #{dev_id} #{exc.to_s}"
    end

    # Submit then the air conditioner is shown
    button = form.button_with(:name => 'btnEquipment')
    page = form.submit(button)
    return page.search('#tblTemperatureList tr:nth-child(1) td:nth-child(4)').text.to_i
  end

  def login
    Mechanize.new.get(@url) do |page|
      # Top menu
      form = page.form_with(:name => 'Form1')

      # Click the enter button
      button = form.button_with(:name => 'btnMainMenu1')
      return form.submit(button)
    end
  end

  def name2dev_id(name)
    return "tcbox#{name[0..2]}0#{name[3..5]}I0#{name[7..8]}"
  end

end
