var margin = [30,50,10,80],
    width = 750 - margin[3] - margin[1],
    height = 300 - margin[0] - margin[2],
    innerHeight = height - 2,
    dimensions = [];

var types = {
  "Number": {
    key: "Number",
    coerce: function(d) { return +d; },
    extent: d3.extent,
    within: function(d, extent, dim) { return extent[0] <= dim.scale(d) && dim.scale(d) <= extent[1]; },
    defaultScale: d3.scaleLinear().range([height, 0])
  },
  "String": {
    key: "String",
    coerce: String,
    extent: function (data) { return data.sort(); },
    within: function(d, extent, dim) { return extent[0] <= dim.scale(d) && dim.scale(d) <= extent[1]; },
    defaultScale: d3.scalePoint().range([0, height])
  }
};

var color = d3.scaleOrdinal()
  .range(["#ccdbdc","#9ad1d4","#80ced7","#007ea7","#73956f","#7bae7f","#95d7ae","#003249","#9bc74","#7b58bc","#5fa7c6","#acdb7a","#57b6ed"]);

var yAxis = d3.axisLeft();

var container = d3.select("#chart")
    .attr("class", "parcoords")
    .style("width", (width + margin[3] + margin[1]) + "px")
    .style("height", (height + margin[0] + margin[2]) + "px");

var svg = container.append("svg")
    .attr("width", width + margin[3] + margin[1])
    .attr("height", height + margin[0] + margin[2])
  .append("g")
    .attr("transform", "translate(" + margin[3] + "," + margin[0] + ")");

var canvas = container.append("canvas")
    .attr("width", width)
    .attr("height", height)
    .style("width", width + "px")
    .style("height", height + "px")
    .style("margin", margin.join("px ") + "px");

var ctx = canvas.node().getContext("2d");
ctx.globalCompositeOperation = 'darken';
ctx.globalAlpha = 0.45;
ctx.lineWidth = 1.5;


d3.csv('data.csv', function(data) {
  keys = d3.keys(data[0]);
  keys.forEach(function(k) {
    if (k == 'NAME') {
      dimensions.push({
        key: k,
        type: types['String'],
        axis: d3.axisLeft()
          .tickFormat(function(d,i) {
            return d;
          })
      });
    } else if (k == 'PRCP') {
      dimensions.push({
        key: k,
        type: types['Number'],
        axis: d3.axisLeft(),
        scale: d3.scaleSqrt().range([height, 0])
      });
    } else {
      dimensions.push({
        key: k,
        type: types['Number'],
        axis: d3.axisRight().tickFormat(function (d){
          return parseInt(d.toString().replace('\,/g',''));
        }),
        scale: d3.scaleSqrt().range([height, 0])
      });
    }
  });

  var xscale = d3.scalePoint()
    .domain(d3.range(dimensions.length))
    .range([0, width]);

  var axes = svg.selectAll(".axis")
      .data(dimensions)
    .enter().append("g")
      .attr("class", function(d) { return "axis " + d.key; })
      .attr("transform", function(d,i) { return "translate(" + xscale(i) + ")"; });

  data = d3.shuffle(data);

  data.forEach(function(d) {
    dimensions.forEach(function(p) {
      d[p.key] = !d[p.key] ? null : p.type.coerce(d[p.key]);
    });

    // truncate long text strings to fit in data table
    for (var key in d) {
      if (d[key] && d[key].length > 35) d[key] = d[key].slice(0,36);
    }
  });

  // type/dimension default setting happens here
  dimensions.forEach(function(dim) {
    if (!("domain" in dim)) {
      // detect domain using dimension type's extent function
      dim.domain = d3_functor(dim.type.extent)(data.map(function(d) { return d[dim.key]; }));
    }
    if (!("scale" in dim)) {
      // use type's default scale for dimension
      dim.scale = dim.type.defaultScale.copy();
    }
    dim.scale.domain(dim.domain);
  });

  var render = renderQueue(draw).rate(50);

  ctx.clearRect(0,0,width,height);
  ctx.globalAlpha = d3.min([0.85/Math.pow(data.length,0.3),1]);
  render(data);

  axes.append("g")
    .each(function(d) {
      var renderAxis = d.axis.scale(d.scale);
      d3.select(this).call(renderAxis);
    })
  .append("text")
    .attr("class", "title")
    .attr("text-anchor", "start")
    .text(function(d) { return "description" in d ? d.description : d.key; });

  // Add and store a brush for each axis.
  axes.append("g")
    .attr("class", "brush")
    .each(function(d) {
      d3.select(this).call(d.brush = d3.brushY()
        .extent([[-10,0], [10,height]])
        .on("start", brushstart)
        .on("brush", brush)
        .on("end", brush)
      )
    })
  .selectAll("rect")
    .attr("x", -8)
    .attr("width", 16);

  d3.selectAll(".axis.NAME .tick text")
    .style("fill", color);




  function project(d) {
    return dimensions.map(function(p,i) {
      // check if data element has property and contains a value
      if (
        !(p.key in d) ||
        d[p.key] === null
      ) return null;

      return [xscale(i),p.scale(d[p.key])];
    });
  }

  function draw(d) {
    ctx.strokeStyle = color(d.NAME);
    ctx.beginPath();
    var coords = project(d);
    coords.forEach(function(p,i) {
      // this tricky bit avoids rendering null values as 0
      if (p === null) {
        // this bit renders horizontal lines on the previous/next
        // dimensions, so that sandwiched null values are visible
        if (i > 0) {
          var prev = coords[i-1];
          if (prev !== null) {
            ctx.moveTo(prev[0],prev[1]);
            ctx.lineTo(prev[0]+6,prev[1]);
          }
        }
        if (i < coords.length-1) {
          var next = coords[i+1];
          if (next !== null) {
            ctx.moveTo(next[0]-6,next[1]);
          }
        }
        return;
      }

      if (i == 0) {
        ctx.moveTo(p[0],p[1]);
        return;
      }

      ctx.lineTo(p[0],p[1]);
    });
    ctx.stroke();
  }

  function brushstart() {
    d3.event.sourceEvent.stopPropagation();
  }

// Handles a brush event, toggling the display of foreground lines.
  function brush() {
    render.invalidate();

    var actives = [];
    svg.selectAll(".axis .brush")
      .filter(function(d) {
        return d3.brushSelection(this);
      })
      .each(function(d) {
        actives.push({
          dimension: d,
          extent: d3.brushSelection(this)
        });
      });

      var selected = data.filter(function(d) {
        if (actives.every(function(active) {
            var dim = active.dimension;
            // test if point is within extents for each active brush
            return dim.type.within(d[dim.key], active.extent, dim);
          })) {
          return true;
        }
  });

  ctx.clearRect(0,0,width,height);
  ctx.globalAlpha = d3.min([0.85/Math.pow(selected.length,0.3),1]);
  render(selected);
  }
});

function d3_functor(v) {
return typeof v === "function" ? v : function() { return v; };
};
