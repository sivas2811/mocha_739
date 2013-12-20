set title sprintf("Partitioning - Consistent Hashing")
#set xrange [500: 700]
#set yrange [-60:-100]
dirname_r = sprintf(".")
dirname_w = sprintf(".")
set ylabel "Number of write requests"
set xlabel "Total number of write requests" offset character 1
set terminal postscript enhanced eps color "Helvetica" 30
set output sprintf("%s/partition.eps", dirname_w)
set style line 1 lc rgb '#B5B5B5' lt 1 lw 4
set style line 2 lc rgb 'black' ps 0 lt 1 lw 2
set key top left nobox 
#set xtics ('521' 1, '545' 21, '557' 41, '581' 61, '617' 81, '629' 101, '683' 121, '689' 141 ) 
set xtics ('5000' 1, '10000' 21, '15000' 41, '20000' 61)
set xtics nomirror 
set ytics nomirror
set border 3 
set ytics out
set ytics scale 2
set xtics scale 0
set style fill pattern 
set grid ytics ls 1
set boxwidth 3
#set label "8.1x\t2.9x" at 46, 5.5
rraa_color = '#A2B5CD'
sample_color = '#4682B4'
scout_color = '#000080'
fourth_color = "blue"

plot sprintf("%s/partition_data.dat", dirname_r) using ($7-4):3 title "Node 1" w boxes fs solid lc rgb rraa_color,\
"" using ($7-1):4 title "Node 2" w boxes fs solid lc rgb sample_color,\
"" using ($7+2):5 title "Node 3" w boxes fs solid lc rgb scout_color,\
"" using ($7+5):6 title "Node 4" w boxes fs solid lc rgb fourth_color



