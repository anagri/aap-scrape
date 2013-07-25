function initAmountMap() {
    init(function (data, title) {
        return amount(data, title);
    }, 0, 5000);
}

function initCountMap() {
    init(function (data, title) {
        return count(data, title);
    }, 0, 10);
}

function findData(data, title) {
    for (var i in data) {
        if (data[i].district.toLowerCase() === title.toLowerCase()) {
            return data[i];
        }
    }
    return null;
}

function count(data, title) {
    var d = findData(data, title);
    return d == null ? "0" : d.num_donors;
}

function amount(data, title) {
    var d = findData(data, title);
    return d == null ? "0" : d.total_amount;
}

function avg_amount(data, title) {
    var d = findData(data, title);
    return d == null ? "0" : d.avg_amount;
}

function init(colorDecider, min, max) {
    var color = d3.scale.linear()
        .range([
            "rgb(255,247,236)",
            "rgb(254,232,200)",
            "rgb(253,212,158)",
            "rgb(253,187,132)",
            "rgb(252,141,89)",
            "rgb(239,101,72)",
            "rgb(215,48,31)",
            "rgb(179,0,0)",
            "rgb(127,0,0)"]);
    d3.csv("materialized_district_level_view.csv", function (data) {
        var svg = d3.select(document.getElementsByTagName("iframe")[0].contentWindow.document);
        color.domain([min, max]);

        function colorFor(title) {
            return color(colorDecider.call(this, data, title));
        }

        svg.selectAll('path')
            .style('stroke', 'black')
            .style('stroke-width', .5)
            .style('fill',function () {
                var title = this.getAttribute('title');
                return colorFor(title);
            }).on("mouseover",function () {
                var district = d3.select(this);
                district.style('fill', d3.rgb(district.style('fill')).darker());
                var title = this.getAttribute('title');
                d3.select('#district')
                    .text(title);
                d3.select('#state')
                    .text(district[0][0].parentElement.getAttribute('title'));
                d3.select('#donors')
                    .text(count(data, title));
                d3.select('#amount')
                    .text(amount(data, title).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                d3.select('#avg_amount')
                    .text(avg_amount(data, title));
            }).on("mouseout", function () {
                var district = d3.select(this);
                district.style('fill', colorFor(this.getAttribute('title')));
                d3.select('#district').text('');
                d3.select('#state').text('');
                d3.select('#donors').text('');
                d3.select('#amount').text('');
                d3.select('#avg_amount').text('');
            });
    });
}
