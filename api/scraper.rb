require 'rubygems'
require 'pp'
require 'yaml'
require 'mechanize'

config = YAML.load_file("#{Dir::pwd}/config.yml")
agent = Mechanize.new

target = 'VAV17E-01'

agent.get(config["url"]) do |page|
  menu_form = page.form_with(:name => 'Form1')
  pp menu_form
  temperature_button = menu_form.button_with(:value => '室温調節')
  pp menu_form.submit(temperature_button)
end



