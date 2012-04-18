require 'rubygems'
require 'pp'
require 'yaml'
require 'mechanize'

config = YAML.load_file("#{Dir::pwd}/config.yml")
agent = Mechanize.new

target = 'tcboxVAV017EI001'

agent.get(config["url"]) do |page|
  menu_form = page.form_with(:name => 'Form1')
  pp menu_form
  temperature_button = menu_form.button_with(:value => '室温調節')
  kucho_page = menu_form.submit(temperature_button)
  form = kucho_page.form_with(:name => 'Form1')
  pp form
  target_checkbox = form.checkbox_with(:name => target)
  pp target_checkbox.check
end



