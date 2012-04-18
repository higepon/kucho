require 'rubygems'
require 'pp'
require 'yaml'
require 'mechanize'

# todo
# remove duplicate
# @url
# intention revealing
# dev_ids
# rename file
# script
class AirRemote
  def initialize(url)
    @url = url
  end

  def change_tempr(conditioners, tempr)
    begin
      agent = Mechanize.new

      target = 'tcboxVAV017EI001'

      agent.get(config["url"]) do |page|

        # Top menu
        menu_form = page.form_with(:name => 'Form1')
        pp menu_form

        # Click the enter button
        temperature_button = menu_form.button_with(:name => 'btnMainMenu1')
        kucho_page = menu_form.submit(temperature_button)

        # Select air conditioners
        form = kucho_page.form_with(:name => 'Form1')
        pp form
        target_checkbox = form.checkbox_with(:name => target)
        pp target_checkbox.check

        # Submit then the air conditioners are shown
        kiki_button = form.button_with(:name => 'btnEquipment')
        pp kiki_button
        reloaded_page = form.submit(kiki_button)
        confirm_form = reloaded_page.form_with(:name => 'Form1')

        hoge.hige
        # Select temperature
        temp_button =  confirm_form.button_with(:name => 'btnTemperatureSetValueButtons2')
        last_page = confirm_form.submit(temp_button)
        pp last_page
        last_form = last_page.form_with(:name => 'Form1')
        # Go!
        #  pp last_form.submit(last_form.button_with(:name => 'btnAdjust'))
      end

    rescue => exc
      STDERR.puts "API internal error: #{exc.to_s}"
      return false
    end
  end

  #change_tempr([], 3)
  def current_value(dev_id)
    #  config = YAML.load_file("#{Dir::pwd}/config.yml")
    agent = Mechanize.new

    agent.get(@url) do |page|

      # Top menu
      menu_form = page.form_with(:name => 'Form1')
      pp menu_form

      # Click the enter button
      temperature_button = menu_form.button_with(:name => 'btnMainMenu1')
      kucho_page = menu_form.submit(temperature_button)

      # Select air conditioners
      form = kucho_page.form_with(:name => 'Form1')
      pp form
      target_checkbox = form.checkbox_with(:name => dev_id)
      pp target_checkbox.check

      # Submit then the air conditioners are shown
      kiki_button = form.button_with(:name => 'btnEquipment')
      pp kiki_button
      reloaded_page = form.submit(kiki_button)
      confirm_form = reloaded_page.form_with(:name => 'Form1')

      pp reloaded_page.search('#tblTemperatureList tr:nth-child(1) td:nth-child(4)').text.to_i
      # id="tblTemperatureList"
    end
  end
end

config = YAML.load_file("#{Dir::pwd}/config.yml")
remote = AirRemote.new(config["url"])
remote.current_value('tcboxABU017EP015')
