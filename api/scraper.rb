require 'rubygems'
require 'pp'
require 'yaml'
require 'mechanize'

config = YAML.load_file("#{Dir::pwd}/config.yml")

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

change_tempr([], 3)
