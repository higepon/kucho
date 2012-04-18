require 'rubygems'
require 'pp'
require 'yaml'
require 'mechanize'

# todo
# remove duplicate
# private
# @url
# intention revealing
# dev_ids
# rename file
# script
class AirRemote
  def initialize(url)
    @url = url
  end

  def cooler!(dev_ids)
    ts dev_ids.map{|dev_id| { :dev_id => dev_id, :temperature => temperature(dev_id) }}
    warmest = ts.max {|a, b| a[:temperature] <=> b[:temperature] }
    return false if (warmest[:temperature] == 1) 
    return nset_temperature!([warmest[:dev_id]], warmest[:temperature] - 1)
  end

  def warmer!(dev_ids)
    ts dev_ids.map{|dev_id| { :dev_id => dev_id, :temperature => temperature(dev_id) }}
    coolest = ts.min {|a, b| a[:temperature] <=> b[:temperature] }
    return false if (coolest[:temperature] == 5) 
    return set_temperature!([warmest[:dev_id]], warmest[:temperature] + 1)
  end

  def set_temperature!(dev_ids, value)
    if (value.class != Fixnum ||
        value > 5 ||
        value < 0) 
      raise "temperature should be 1, 2, 3, 4 or 5"
    end

    begin

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
      pp "success"
      # Go!
      #  pp last_form.submit(last_form.button_with(:name => 'btnAdjust'))
      return true
    rescue => exc
      STDERR.puts "internal error: #{exc.to_s}"
      return false
    end
  end

  def temperature(dev_id)
    sleep 1
    # Select air conditioner
    form = login.form_with(:name => 'Form1')
    form.checkbox_with(:name => dev_id).check

    # Submit then the air conditioner is shown
    button = form.button_with(:name => 'btnEquipment')
    page = form.submit(button)
    return page.search('#tblTemperatureList tr:nth-child(1) td:nth-child(4)').text.to_i
  end

private
  def login
    Mechanize.new.get(@url) do |page|
      # Top menu
      form = page.form_with(:name => 'Form1')

      # Click the enter button
      button = form.button_with(:name => 'btnMainMenu1')
      return form.submit(button)
    end
  end
end

config = YAML.load_file("#{Dir::pwd}/config.yml")
remote = AirRemote.new(config["url"])
pp remote.cooler!(['tcboxVAV017EI001', 'tcboxVAV017EI002'])
