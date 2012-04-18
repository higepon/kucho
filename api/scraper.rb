require 'rubygems'
require 'pp'
require 'yaml'
require 'mechanize'

config = YAML.load_file("#{Dir::pwd}/config.yml")
agent = Mechanize.new
page = agent.get(config["url"])
pp page

