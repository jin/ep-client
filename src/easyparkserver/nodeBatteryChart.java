/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package easyparkserver;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class nodeBatteryChart extends JPanel{
    private TimeSeries battNode1,battNode2,battNode3,battNode4,battNode5;

    public nodeBatteryChart(int maxAge){
        super(new BorderLayout());

        this.battNode1 = new TimeSeries("Node 1", Second.class);
        this.battNode2 = new TimeSeries("Node 2", Second.class);
        this.battNode3 = new TimeSeries("Node 3", Second.class);
        this.battNode4 = new TimeSeries("Node 4", Second.class);
        this.battNode5 = new TimeSeries("Node 5", Second.class);

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        dataset.addSeries(this.battNode1);
        dataset.addSeries(this.battNode2);
        dataset.addSeries(this.battNode3);
        dataset.addSeries(this.battNode4);
        dataset.addSeries(this.battNode5);

        DateAxis domain = new DateAxis("Time");
        NumberAxis range = new NumberAxis("Battery percentage");
        domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
        range.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));

        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL));
        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        domain.setAutoRange(true);
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);
        domain.setTickLabelsVisible(true);

        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        JFreeChart chart = new JFreeChart("Energy levels",
                new Font("SansSerif", Font.BOLD, 24), plot, true);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(Color.black)));
        add(chartPanel);
    }

    private void updateTimeSeries(int node, int batt){
        switch(node){
            case 1: this.battNode1.addOrUpdate(new Second(), batt); break;
            case 2: this.battNode2.addOrUpdate(new Second(), batt); break;
            case 3: this.battNode3.addOrUpdate(new Second(), batt); break;
            case 4: this.battNode4.addOrUpdate(new Second(), batt); break;
            case 5: this.battNode5.addOrUpdate(new Second(), batt); break;
            default: break;
        }
    }
    /**
     * The data generator.
     */
    class DataGenerator extends Timer implements ActionListener {

        /**
         * Constructor.
         *
         * @param interval the interval (in milliseconds)
         */
        DataGenerator(int interval) {
            super(interval, null);
            addActionListener(this);
        }

        /**
         * Adds current battery level readings to the dataset.
         *
         * @param event the action event.
         */
        public void actionPerformed(ActionEvent event) {
            int y = 4;
            for (int i = 1; i <= 5; i++){
                updateTimeSeries(i, Integer.parseInt(EasyparkServerView.serverTokens[y]));
                y = y + 6;
            }
        }
    }
}
