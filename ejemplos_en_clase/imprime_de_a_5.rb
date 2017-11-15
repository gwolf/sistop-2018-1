#!/usr/bin/ruby

mutex = Mutex.new

def imprime(txt)
  puts "Texto a imprimir: " + txt
  mutex.lock
  while true
    puts txt
    sleep(0.2)
  end
  mutex.unlock
end

Thread.new { imprime(',') }
Thread.new { imprime('.') }
Thread.new { imprime('!') }
Thread.new { imprime(':') }
sleep(10)
