// License: GPL. For details, see LICENSE file.
package at.dallermassl.josm.plugin.surveyor.action;

import java.util.List;

import javax.swing.Action;

import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.tagging.presets.TaggingPreset;
import org.openstreetmap.josm.gui.tagging.presets.TaggingPresets;

import at.dallermassl.josm.plugin.surveyor.GpsActionEvent;
import at.dallermassl.josm.plugin.surveyor.SurveyorAction;

/**
 * @author cdaller
 *
 */
public class TaggingPresetAction implements SurveyorAction {
    private String presetName;
    private TaggingPreset preset;

    @Override
    public void actionPerformed(GpsActionEvent event) {
        if (preset == null) {
            return;
        }
        LatLon coordinates = event.getCoordinates();
        System.out.println(getClass().getSimpleName() + " KOORD: " + coordinates.lat() + ", "
            + coordinates.lon() + ", preset=" + presetName);
//        Node node = new Node(coordinates);
//        node.put("created_by", "JOSM-surveyor-plugin");
//        synchronized(LiveGpsLock.class) {
//            Main.main.editLayer().data.nodes.add(node);
//            Main.ds.setSelected(node);
//        }
//        Main.map.repaint();

        // call an annotationpreset to add additional properties...
        preset.actionPerformed(null);

    }

    @Override
    public void setParameters(List<String> parameters) {
        if (parameters.size() == 0) {
            throw new IllegalArgumentException("No annotation preset name given!");
        }
        presetName = parameters.get(0);
        preset = getAnnotationPreset(presetName);
        if (preset == null) {
            System.err.println("No valid preset '" + parameters.get(0) + "' found - disable action!");
            return;
        }
    }

    /**
     * Returns the preset with the given name or <code>null</code>.
     * @param name the name of the annotation preset.
     * @return  the preset with the given name.
     */
    protected TaggingPreset getAnnotationPreset(String name) {
        for (TaggingPreset preset : TaggingPresets.getTaggingPresets()) {
            if (name.equals(preset.getValue(Action.NAME))) {
                return preset;
            }
        }
        return null;
    }
}
