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

  def set_temperature(dev_ids, tempr)
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
      temp_button =  confirm_form.button_with(:name => 'btnTemperatureSetValueButtons2')
      last_page = confirm_form.submit(temp_button)
      pp last_page
      last_form = last_page.form_with(:name => 'Form1')
      # Go!
      #  pp last_form.submit(last_form.button_with(:name => 'btnAdjust'))

    rescue => exc
      STDERR.puts "API internal error: #{exc.to_s}"
      return false
    end
  end

  def temperature(dev_id)

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
    agent = Mechanize.new
    agent.get(@url) do |page|
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
#pp remote.temperature('tcboxABU017EP015')
pp remote.set_temperature(['tcboxVAV017EI001'], 3)
