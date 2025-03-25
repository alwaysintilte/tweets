const width = 1500;
const height = 1000;

const svg = d3.select("svg");

const projection = d3.geoAlbersUsa().scale(1500).translate([width / 2, height / 2]);
const path = d3.geoPath().projection(projection);

const colorScale = d3.scaleLinear()
    .domain([-1, 0, 1])
    .range(["#0437F2", "white", "yellow"]);

const tooltip = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("visibility", "hidden");

const stateData = {};

d3.text("data/states-data.txt").then((data) => {
    data.split("\n").forEach(line => {
        const [abbr, name, info, flag] = line.split(":");
        stateData[abbr.trim()] = {
            name: name.trim(),
            info: info ? info.trim() : "нет информации",
            flag: flag ? flag.trim() : null
        };
    });
});

d3.text("data/states.txt").then((data) => {
    const stateColors = {};
    data.split(";").forEach(pair => {
        const [state, value] = pair.split(":");
        stateColors[state.trim()] = +value;
    });

    d3.json("libs/states-10m.json").then((us) => {
        const states = topojson.feature(us, us.objects.states);

        svg.append("g")
            .selectAll("path")
            .data(states.features)
            .join("path")
            .attr("d", path)
            .attr("fill", d => {
                const stateAbbr = d.properties.name;
                const value = stateColors[stateAbbr];
                if (value === undefined) return "#9c9c9c";
                return colorScale(value);
            })
            .attr("stroke", "#3d3d3d")
            .attr("stroke-width", 1)
            .on("mouseover", function(event, d) {
                const stateAbbr = d.properties.name;
                const fullName = stateData[stateAbbr]?.name || stateAbbr;
                const value = stateColors[stateAbbr];

                tooltip.style("visibility", "visible")
                    .style("width", "auto")
                    .style("height", "auto")
                    .html(`
            <strong>${fullName}</strong><br>
            Значение: ${value !== undefined ? value : "нет данных"}
        `);
            })

            .on("mousemove", function(event) {
                tooltip.style("top", (event.pageY + 10) + "px")
                    .style("left", (event.pageX + 10) + "px");
            })
            .on("mouseout", function() {
                tooltip.style("visibility", "hidden");
            })
            .on("click", function(event, d) {
                const stateAbbr = d.properties.name;
                const fullName = stateData[stateAbbr]?.name || stateAbbr;
                const value = stateColors[stateAbbr];

                const info = stateData[stateAbbr]?.info || "нет дополнительной информации";
                const flag = stateData[stateAbbr]?.flag;

                tooltip.style("visibility", "visible")
                    .style("width", "300px")
                    .style("height", "auto")
                    .style("top", (event.pageY + 10) + "px")
                    .style("left", (event.pageX + 10) + "px")
                    .html(`
            <strong>${fullName}</strong><br>
            Значение: ${value !== undefined ? value : "нет данных"}<br>
            ${flag ? `<img src="${flag}" alt="${fullName} Flag" style="width: 100px; height: auto; margin: 10px 0;">` : ""}
            <p style="margin-top: 10px;">${info}</p>
        `);
            })


        svg.append("g")
            .selectAll("text")
            .data(states.features)
            .join("text")
            .attr("x", d => path.centroid(d)[0])
            .attr("y", d => path.centroid(d)[1])
            .attr("text-anchor", "middle")
            .attr("font-size", "8px")
            .attr("fill", "black")
            .text(d => d.properties.name);
    });
});
