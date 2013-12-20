set title sprintf("Read Scalability")
set xrange [0: 3000]
set yrange [0:1100]
dirname_r = sprintf(".")
dirname_w = sprintf(".")
set ylabel "Completion Time (seconds)"
set xlabel "Total number of parallel read requests" offset character 1
set terminal postscript enhanced eps color "Helvetica" 30
set output sprintf("%s/read_scalability.eps", dirname_w)
set style line 1 lc rgb 'white' lt 1 lw 4
set style line 2 lc rgb 'white' ps 0 lt 1 lw 2
set key top left nobox 
#set xtics ('300' 1, '900' 21, '1500' 41, '2100' 61, '3000' 81)
set xtics nomirror 
set ytics nomirror
set border 3 
set ytics out
set ytics scale 2
set xtics 600
set style fill pattern 
set grid ytics ls 1
#set boxwidth 3
#set label "8.1x\t2.9x" at 46, 5.5
rraa_color = '#A2B5CD'
sample_color = '#4682B4'
scout_color = '#000080'
fourth_color = "blue"

plot sprintf("%s/read_scal.dat", dirname_r) using 2:3 title "5000 requests" w lines linecolor rgb sample_color,\
"" using 2:4 title "10000 requests" w lines linecolor rgb fourth_color



