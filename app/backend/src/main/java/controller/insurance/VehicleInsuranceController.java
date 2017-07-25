package controller.insurance;

import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.VehicleInsuranceDAO;
import model.account.Function;
import model.account.Resource;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.billing.VehicleInvoice;
import model.fleet.Vehicle;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class VehicleInsuranceController extends AbstractController<VehicleInsurance> {

    private final DAOManager manager;
    private VehicleInsuranceDAO dao;

    public VehicleInsuranceController(Function function, DAOManager manager) {
        super(manager, manager.getVehicleInsuranceDao(), Resource.INSURANCE, function);
        this.manager = manager;
        this.dao = manager.getVehicleInsuranceDao();
    }


    public void archive(UUID uuid, LocalDate date) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        VehicleInsurance insurance = get(uuid);
        Invoice currentStatement = manager.getInvoiceDao().get(insurance.getVehicle().getFleet().getOwner().getCurrentStatement().getUuid());
        if (!date.isAfter(currentStatement.getEndDate().toLocalDate())) {
            //Add as Correction
            if (date.isBefore(LocalDate.now())) {
                Period period = Period.between(date, LocalDate.now());
                int months = period.getMonths();
                int days = Period.between(date.plusMonths(months), LocalDate.now()).getDays();
                VehicleInvoice vehicleInvoice = createVehicleInvoice(insurance, insurance.calculateTax(), insurance.calculateCost(), -months, -days);
                createCorrection(currentStatement, vehicleInvoice);
            }
            //Add to current statement
            Period period = Period.between(LocalDate.now(), currentStatement.getEndDate().toLocalDate());
            int months = period.getMonths();
            int days = Period.between(date.plusMonths(months), LocalDate.now()).getDays();
            VehicleInvoice vehicleInvoice = currentStatement.getVehicleInvoice(uuid);
            if (vehicleInvoice == null) {
                vehicleInvoice = createVehicleInvoice(insurance, insurance.calculateTax(), insurance.calculateCost(), -months, -days);
                currentStatement.getVehicleInvoices().add(vehicleInvoice);
            } else {
                LocalDate now = LocalDate.now();
                double monthFactor = (double) days / (double) now.getMonth().length(now.isLeapYear());
                vehicleInvoice.setTotalTax(vehicleInvoice.getTotalTax() - insurance.calculateTax() * months - (int) Math.round(monthFactor * insurance.calculateTax()));
                vehicleInvoice.setTotalCost(vehicleInvoice.getTotalCost() - insurance.calculateCost() * months - (int) Math.round(monthFactor * insurance.calculateCost()));
            }
            manager.getInvoiceDao().update(currentStatement);
        }
        super.archive(uuid);
    }


    public VehicleInsurance create(VehicleInsurance insurance) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        VehicleInsurance vehicleInsurance = insurance;
        LocalDate date = vehicleInsurance.getStartDate().toLocalDate();
        Invoice currentStatement = null;
        try {
            currentStatement = vehicleInsurance.getVehicle().getFleet().getOwner().getCurrentStatement();
            // if there is no current statement for the Insurance, just create and return
            if (currentStatement == null) {
                return super.create(vehicleInsurance);
            }
            //If the given Insurance has a non-empty currentStatement field, continue further to adjust the statement accordingly
            currentStatement = manager.getInvoiceDao().get(currentStatement.getUuid());
        } catch (ObjectNotFoundException e) {
            return super.create(insurance);
        }


        if (!date.isAfter(currentStatement.getEndDate().toLocalDate())) {// if insurance start-date comes before the end of the current statement
            //Add as Correction if the start-date of the Insurance overlaps with the current statement
            if (date.isBefore(LocalDate.now())) {
                Period period = Period.between(date, LocalDate.now());
                int months = period.getMonths();
                int days = Period.between(date.plusMonths(months), LocalDate.now()).getDays();
                VehicleInvoice vehicleInvoice = createVehicleInvoice(vehicleInsurance, vehicleInsurance.calculateTax(), vehicleInsurance.calculateCost(), months, days);

                createCorrection(currentStatement, vehicleInvoice);
            }
            //Add to current statement
            Period period = Period.between(LocalDate.now(), currentStatement.getEndDate().toLocalDate());
            int months = period.getMonths();
            int days = Period.between(date.plusMonths(months), currentStatement.getEndDate().toLocalDate()).getDays();


            VehicleInvoice vehicleInvoice = createVehicleInvoice(vehicleInsurance, vehicleInsurance.calculateTax(), vehicleInsurance.calculateCost(), months, days);
            currentStatement.getVehicleInvoices().add(vehicleInvoice);
            vehicleInsurance = super.create(vehicleInsurance);
            vehicleInvoice.setVehicleInsuranceID(vehicleInsurance.getUuid());
            manager.getInvoiceDao().update(currentStatement);
            return vehicleInsurance;
        }
        return super.create(vehicleInsurance);
    }

    public VehicleInsurance update(VehicleInsurance insurance, LocalDate date) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        VehicleInsurance vehicleInsurance = insurance;
        Invoice currentStatement = manager.getInvoiceDao().get(vehicleInsurance.getVehicle().getFleet().getOwner().getCurrentStatement().getUuid());
        VehicleInsurance currentInsurance = get(vehicleInsurance.getUuid());
        if (!date.isAfter(currentStatement.getEndDate().toLocalDate())) {
            vehicleInsurance = super.update(vehicleInsurance);
            int taxDifference = vehicleInsurance.calculateTax() - currentInsurance.calculateTax();
            int costDifference = vehicleInsurance.calculateCost() - currentInsurance.calculateCost();
            if (date.isBefore(LocalDate.now())) {
                Period period = Period.between(date, LocalDate.now());
                int months = period.getMonths();
                int days = Period.between(date.plusMonths(months), LocalDate.now()).getDays();
                VehicleInvoice vehicleInvoice = createVehicleInvoice(vehicleInsurance, taxDifference, costDifference, months, days);

                createCorrection(currentStatement, vehicleInvoice);
            }
            //Add to current statement
            Period period = Period.between(LocalDate.now(), currentStatement.getEndDate().toLocalDate());
            int months = period.getMonths();
            int days = Period.between(date.plusMonths(months), currentStatement.getEndDate().toLocalDate()).getDays();
            VehicleInvoice vehicleInvoice = currentStatement.getVehicleInvoice(currentInsurance.getUuid());
            if (vehicleInvoice == null) {
                vehicleInvoice = createVehicleInvoice(vehicleInsurance, taxDifference, costDifference, months, days);
                currentStatement.getVehicleInvoices().add(vehicleInvoice);
            } else {
                LocalDate now = LocalDate.now();
                double monthFactor = (double) days / (double) now.getMonth().length(now.isLeapYear());
                vehicleInvoice.setTotalTax(vehicleInvoice.getTotalTax() + taxDifference * months + (int) Math.round(monthFactor * taxDifference));
                vehicleInvoice.setTotalCost(vehicleInvoice.getTotalCost() + costDifference * months + (int) Math.round(monthFactor * costDifference));
            }

            manager.getInvoiceDao().update(currentStatement);
            return vehicleInsurance;
        } else {
            return super.update(vehicleInsurance);
        }
    }

    private VehicleInvoice createVehicleInvoice(VehicleInsurance insurance, int tax, int cost, int months, int days) throws DataAccessException {
        VehicleInvoice vehicleInvoice = new VehicleInvoice();
        LocalDate now = LocalDate.now();
        vehicleInvoice.setInsuredValue(insurance.getInsuredValue());
        vehicleInvoice.setFranchise(insurance.getFranchise());
        vehicleInvoice.setVin(insurance.getVehicle().getVin());
        vehicleInvoice.setLicensePlate(insurance.getVehicle().getLicensePlate());
        vehicleInvoice.setVehicleInsuranceID(insurance.getUuid());

        double monthFactor = (double) days / (double) now.getMonth().length(now.isLeapYear());
        vehicleInvoice.setTotalTax(tax * months + (int) Math.round(monthFactor * tax));
        vehicleInvoice.setTotalCost(cost * months + (int) Math.round(monthFactor * cost));
        try {
            vehicleInvoice.setSuretyType(manager.getSuretyDao().get(insurance.getSurety().getUuid()).getSuretyType());
        } catch (ObjectNotFoundException e) {
            throw new DataAccessException(e);
        }
        return vehicleInvoice;
    }

    private void createCorrection(Invoice currentStatement, VehicleInvoice vehicleInvoice) throws DataAccessException, ConstraintViolationException {
        Invoice correction = new Invoice();
        correction.setStartDate(LocalDateTime.now());
        correction.setEndDate(LocalDateTime.now());
        correction.setPayer(currentStatement.getPayer());
        correction.setType(InvoiceType.CORRECTION);
        correction.setPaid(false);
        correction.getVehicleInvoices().add(vehicleInvoice);
        manager.getInvoiceDao().create(correction);
    }

    @Override
    public boolean isOwner(VehicleInsurance insurance, Function function) {
        return insurance.getVehicle().getFleet().getOwner().equals(function.getCompany());
    }

    public Collection<VehicleInsurance> getBy(Vehicle vehicle) throws DataAccessException, UnAuthorizedException {
        return vehicle == null ? getAll() : getAll(dao.byVehicle(vehicle));
    }

    public Collection<VehicleInsurance> getFiltered(Contract contract, Vehicle vehicle) throws DataAccessException, UnAuthorizedException {

        return getAll(dao.byVehicle(vehicle), dao.byContract(contract));

    }
}
